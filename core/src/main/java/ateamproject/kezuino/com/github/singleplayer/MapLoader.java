package ateamproject.kezuino.com.github.singleplayer;

import ateamproject.kezuino.com.github.utility.game.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Vector2;

import java.util.*;
import java.util.function.Consumer;

/**
 * Loads the {@link Map}. Also allows for partial map loading for network synchronization.
 */
public class MapLoader {
    protected Map map;
    protected GameSession session;
    protected String mapPath;
    protected String mapName;
    protected TiledMap tiledMap;
    protected EnumSet<MapObjectTypes> typesToLoad;
    protected HashMap<Class, List<Consumer>> consumers;

    public MapLoader(GameSession session, String mapName) {
        if (session == null) throw new IllegalArgumentException("Parameter session must not be null.");
        if (mapName == null || mapName.isEmpty())
            throw new IllegalArgumentException("Parameter mapName must not be null or empty.");

        String path = "maps/" + mapName + "/map.tmx";
        if (!Gdx.files.internal(path).exists())
            throw new IllegalStateException(String.format("File '%s' doesn't exist.", mapName));
        this.session = session;
        this.mapPath = path;
        this.mapName = mapName;
        this.consumers = new HashMap<>();
        this.typesToLoad = EnumSet.allOf(MapObjectTypes.class);
    }

    public EnumSet<MapObjectTypes> getTypesToLoad() {
        return typesToLoad;
    }

    public void setTypesToLoad(EnumSet<MapObjectTypes> typesToLoad) {
        this.typesToLoad = typesToLoad;
    }

    public Map getMap() {
        return map;
    }

    /**
     * Creates a new {@link Map} based on the dimensions specified in the TMX file.
     *
     * @return Empty {@link Map} with the right dimensions.
     */
    protected Map createMap() {
        TiledMap tiledMap = new TmxMapLoader().load(mapPath);
        MapProperties mapProps = tiledMap.getProperties();
        Map map = new Map(session, mapProps.get("width", Integer.class), mapProps.get("height", Integer.class));
        this.tiledMap = tiledMap;
        map.setBaseMap(tiledMap);
        return map;
    }

    /**
     * Initializes the {@link Map} with background {@link Node nodes}.
     *
     * @return {@link Map} with loaded {@link Node nodes} only.
     */
    protected Map createNodes() {
        // Convert TMX data to game tiles.
        TiledMapTileLayer background = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Node node = map.getNode(x, y);

                // Set background tiles.
                node.setTileId(background.getCell(x, y).getTile().getId());
            }
        }
        return map;
    }

    /**
     * Fully loads the {@link Map} from the TMX file.
     */
    public void load() {
        this.map = createMap();
        createNodes();
        createObjects();
    }

    public <T> void addConsumer(Class<T> clazz, Consumer<T> consumer) {
        if (!consumers.containsKey(clazz)) {
            consumers.put(clazz, new ArrayList<>());
        }
        List<Consumer> list = consumers.get(clazz);
        list.add(consumer);
    }

    protected <T> void runConsumers(Class<?> type, T obj) {
        if (!consumers.containsKey(type)) return;
        for (Consumer consumer : consumers.get(type)) {
            consumer.accept(obj);
        }
    }

    protected void createObjects() {
        for (MapLayer layer : tiledMap.getLayers()) {
            for (MapObject loopObj : layer.getObjects()) {
                if (!(loopObj instanceof TextureMapObject)) continue;
                TextureMapObject obj = (TextureMapObject) loopObj;

                TiledMapTileSet objTileLayer = tiledMap.getTileSets().getTileSet(layer.getName());
                MapProperties tileSetProps = objTileLayer.getProperties();

                MapProperties objProps = loopObj.getProperties();
                MapProperties objTileProps = objTileLayer.getTile(objProps.get("gid", int.class)).getProperties();

                Vector2 curPos = new Vector2(objProps.get("x", float.class), objProps.get("y", float.class));
                Node posNode = map.getNode(curPos);

                if (getTypesToLoad().contains(MapObjectTypes.ITEM) && objTileProps.containsKey(MapObjectTypes.ITEM.getKey())) {
                    // Create item.
                    String itemTypeName = objTileProps.get("item", String.class);
                    ItemType itemType = Arrays.stream(ItemType.values())
                                              .filter(e -> e.name().equalsIgnoreCase(itemTypeName))
                                              .findAny()
                                              .orElse(null);
                    Item item = new Item(curPos, itemType);
                    item.setMap(map);
                    item.setTexture(obj.getTextureRegion());
                    posNode.setItem(item);

                    runConsumers(MapObjectTypes.ITEM.getType(), item);
                } else if (getTypesToLoad().contains(MapObjectTypes.ENEMY) && objTileProps.containsKey(MapObjectTypes.ENEMY
                        .getKey())) {
                    // Create enemy.
                    Enemy enemy = new Enemy(null, curPos, 2.5f, Direction.Down);

                    enemy.setTexture(obj.getTextureRegion());
                    enemy.setMap(map);
                    enemy.setId();
                    map.addGameObject(enemy);

                    runConsumers(MapObjectTypes.ENEMY.getType(), enemy);
                } else if (getTypesToLoad().contains(MapObjectTypes.PACTALE) && objTileProps.containsKey(MapObjectTypes.PACTALE
                        .getKey())) {
                    // Get playerIndex from object properties.
                    int playerIndex = -1;
                    if (objProps.containsKey("index")) {
                        playerIndex = Integer.valueOf(objProps.get("index", String.class));
                    }

                    // Create pactale.
                    Pactale pactale = new Pactale(playerIndex, curPos, 3, 3f, Direction.Down, Color.WHITE);
                    pactale.setTexture(obj.getTextureRegion());
                    pactale.setId();
                    map.addGameObject(pactale);

                    runConsumers(MapObjectTypes.PACTALE.getType(), pactale);
                }
            }
        }
    }


    public enum MapObjectTypes {
        PACTALE("isPactale", Pactale.class),
        ENEMY("isEnemy", Enemy.class),
        ITEM("item", Item.class);

        /**
         * Key to search for in the TMX file.
         */
        protected String key;
        /**
         * Type of object.
         */
        protected Class<?> type;

        /**
         * Initializes the enum value with the related key to search for in the TMX file.
         *
         * @param key  Key to search for in the TMX file.
         * @param type Type of object.
         */
        MapObjectTypes(String key, Class<?> type) {
            if (key == null || key.isEmpty())
                throw new IllegalArgumentException("Parameter key must not be null or empty.");
            if (type == null) throw new IllegalArgumentException("Parameter type must not be null or empty.");
            this.key = key;
            this.type = type;
        }

        /**
         * @return Gets the {@link #type}.
         * @see #type
         */
        public Class<?> getType() {
            return type;
        }

        /**
         * @return Gets the {@link #key}.
         * @see #key
         */
        public String getKey() {
            return key;
        }
    }
}
