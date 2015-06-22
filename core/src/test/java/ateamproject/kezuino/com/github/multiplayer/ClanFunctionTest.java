/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.multiplayer;

import ateamproject.kezuino.com.github.network.packet.enums.InvitationType;
import ateamproject.kezuino.com.github.network.packet.enums.ManagementType;
import ateamproject.kezuino.com.github.singleplayer.ClanFunctions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Sven
 */
public class ClanFunctionTest {

    private ClanFunctions clanFunctions = ClanFunctions.getInstance();

    public ClanFunctionTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFillTable() {
        /**
         * Gives all clans for a specific {@code emailaddress}.
         *
         * @param emailaddress the emailaddress for which the clans needs to
         * searched for.
         * @return the String array which contains all the clan names.
         */
        List<String> result = clanFunctions.getClansByEmailAddress("jip.vandevijfeike@gmail.com");
        assertEquals("MBoiz", result.get(0));

    }

    @Test
    public void testCreateClan() {
        /**
         * Create a clan with the given {@code clanName} and {@code emailaddress}.
         *
         * @param clanName Name of the clan.
         * @param emailaddress Creater of the clan.
         * @return True if creation did succeed else false.
         */
        //Toevoegen van een clannaam die nog niet gebruikt is en een email van een geldig account resultaat moet true zijn.
        assertTrue(clanFunctions.createClan("MboizSven", "svenke@gmail.com"));
        //Toevoegen van dezelfde clan door dezelfde speler zou false als resultaat moeten genereren
        assertFalse(clanFunctions.createClan("MboizSven", "svenke@gmail.com"));
        //Toevoegen van een geldige clan naam maar met email die niet in de database zit, zou false moeten genereren
        assertFalse(clanFunctions.createClan("MboizSven", "nepmail@nepmail.nep"));
    }

    @Test
    public void testGetInvitation() {
        /**
         * Gets the
         * {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.InvitationType}
         * for a given clan and a given {@code emailaddress}.
         *
         * @param clanName Name of the clan.
         * @param emailaddress {@code emailaddress} of who is in the clan.
         * @return
         * {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.InvitationType},
         * or null if failed.
         */
        //De invitation type van de clan MBoiz van jip halen, dit moet accepted zijn.       
        assertEquals(InvitationType.INVITE, clanFunctions.getInvitation("MBoiz", "jip.vandevijfeike@gmail.com"));
        //De invitation type van de clan Mboiz2 van jip moet ook accepted zijn.
        assertEquals(InvitationType.INVITE, clanFunctions.getInvitation("Mboiz2", "jip.vandevijfeike@gmail.com"));
        assertNull(clanFunctions.getInvitation("Nepclan", "Nepemail"));
    }

    @Test
    public void testGetManagement() {
        /**
         * Gets the managementType for a given clan and a given {
         *
         * @copde emailaddress}.
         *
         * @param clanName Name of the clan
         * @param emailaddress Emailaddress of who is in the clan
         * @return
         * {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.ManagementType},
         * or null if failed.
         */
        //De user Jip can Remove van de clan MBoiz
        assertEquals(ManagementType.REMOVE, clanFunctions.getManagement("MBoiz", "jip.vandevijfeike@gmail.com"));
        //De user Sven kan rejecten want is nog niet gejoined bij MBoiz
        assertEquals(ManagementType.REJECT, clanFunctions.getManagement("MBoiz", "svenke@gmail.com"));
        //De user Sven kan Mboiz2 leaven omdat hij gejoined is maar niet leider
        assertEquals(ManagementType.LEAVE, clanFunctions.getManagement("Mboiz2", "svenke@gmail.com"));
    }

    @Test
    public void testHandleInvitation() {
        /**
         * Processes an invitation.
         *
         * @param invite Invite to process.
         * @param clanName Name of the clan where the invite belongs to.
         * @param emailAddress Email of the person who did send the invite to this
         * function.
         * @param nameOfEmailInvitee Optional name/emailaddress parameter for the
         * 'INVITE' invite. Needs to be null when a invite is not of the type
         * 'INVITE'.
         * @return True if invitation is successfully handled else false.
         */
        //Leeg email adres van verzender mag niet de invitation status van andere aanpassen.
        assertFalse(clanFunctions.handleInvitation(InvitationType.ACCEPT, "MBoiz", "randomemail", "svenke@gmail.com"));
        /*BUG ER WORD NIET GECHECKED NAAR DE EMAIL VAN DEGENE DIE DE INVITE VERSTUURDE*/
        //De eigenaar mag dit echter wel doen
        assertTrue(clanFunctions.handleInvitation(InvitationType.ACCEPT, "MBoiz", "jip.vandevijfeike@gmail.com", "svenke@gmail.com"));
        //De invitation type kan niet opnieuw naar hetzelfde geset worden daarom false als dit opnieuw word geprobeerd
        assertFalse(clanFunctions.handleInvitation(InvitationType.ACCEPT, "MBoiz", "jip.vandevijfeike@gmail.com", "svenke@gmail.com"));
        //Returned gewoon opnieuw true
    }

    @Test
    public void testHandleManagement() {
        /**
         * Executes an action based on the given
         * {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.ManagementType managementType}
         * to run on the {@code clan} and {@code emailaddress}.
         *
         * @param managementType
         * {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.ManagementType}
         * to process.
         * @param clanName Name of the clan where the
         * {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.ManagementType}
         * belongs to
         * @param emailaddress Emailaddress of the user.
         * @return True if
         * {@link ateamproject.kezuino.com.github.render.screens.ClanFunctions.ManagementType}
         * is successfully handled else false.
         */
        //Sven kan de invitation naar clan 1 nog rejecten en gaat dat met deze doen zou true moeten returnen
        assertTrue(clanFunctions.handleManagement(ManagementType.REJECT, "MBoiz", "svenke@gmail.com"));
        //Als sven een clan probeert te removen waarvan hij niet de leider is zal dit false returnen
        assertFalse(clanFunctions.handleManagement(clanFunctions.getManagement("svenke@gmail.com", "Mboiz2"), "Mboiz2", "svenke@gmail.com"));
        //Als Jip dit probeert zou dit wel moeten lukken en moet er false worden returned
        assertTrue(clanFunctions.handleManagement(ManagementType.REMOVE, "Mboiz2", "jip.vandevijfeike@gmail.com"));
    }

}
