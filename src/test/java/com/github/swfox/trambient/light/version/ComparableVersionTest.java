package com.github.swfox.trambient.light.version;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComparableVersionTest {

    @Test
    public void parseSemVer_1_1_1_ok(){
        ComparableVersion version = new ComparableVersion("1.1.1");
        assertEquals("1.1.1",version.toString());
    }

    @Test
    public void parseSemVer_1_2_3_SNAPSHOT_ok(){
        ComparableVersion version = new ComparableVersion("1.2.3-SNAPSHOT");
        assertEquals("1.2.3-SNAPSHOT",version.toString());
    }

    @Test
    public void compareSemVer_1_0_0_and_2_0_0_true(){
        ComparableVersion version = new ComparableVersion("1.0.0");
        boolean result = version.isOlderThan("2.0.0");
        assertTrue(result);
    }

    @Test
    public void compareSemVer_1_0_0_and_1_1_0_true(){
        ComparableVersion version = new ComparableVersion("1.0.0");
        boolean result = version.isOlderThan("1.1.0");
        assertTrue(result);
    }

    @Test
    public void compareSemVer_1_0_0_and_1_0_1_true(){
        ComparableVersion version = new ComparableVersion("1.0.0");
        boolean result = version.isOlderThan("1.0.1");
        assertTrue(result);
    }

    @Test
    public void compareSemVer_1_1_0_and_1_0_1_false(){
        ComparableVersion version = new ComparableVersion("1.1.0");
        boolean result = version.isOlderThan("1.0.1");
        assertFalse(result);
    }

    @Test
    public void compareSemVer_2_1_0_and_2_0_0_false(){
        ComparableVersion version = new ComparableVersion("2.1.0");
        boolean result = version.isOlderThan("2.0.0");
        assertFalse(result);
    }

    @Test
    public void compareSemVer_2_0_0_and_1_0_0_false(){
        ComparableVersion version = new ComparableVersion("2.0.0");
        boolean result = version.isOlderThan("1.0.0");
        assertFalse(result);
    }

    @Test
    public void compareSemVer_1_0_0_and_1_0_0_false(){
        ComparableVersion version = new ComparableVersion("1.0.0");
        boolean result = version.isOlderThan("1.0.0");
        assertFalse(result);
    }

    @Test
    public void compareSemVerIsNewer_1_0_0_and_2_0_0_true(){
        ComparableVersion version = new ComparableVersion("1.0.0");
        boolean result = version.isNewerThan("2.0.0");
        assertFalse(result);
    }

    @Test
    public void compareSemVerIsNewer_1_0_0_and_1_1_0_true(){
        ComparableVersion version = new ComparableVersion("1.0.0");
        boolean result = version.isNewerThan("1.1.0");
        assertFalse(result);
    }

    @Test
    public void compareSemVerIsNewer_1_0_0_and_1_0_1_true(){
        ComparableVersion version = new ComparableVersion("1.0.0");
        boolean result = version.isNewerThan("1.0.1");
        assertFalse(result);
    }

    @Test
    public void compareSemVerIsNewer_1_1_0_and_1_0_1_false(){
        ComparableVersion version = new ComparableVersion("1.1.0");
        boolean result = version.isNewerThan("1.0.1");
        assertTrue(result);
    }

    @Test
    public void compareSemVerIsNewer_2_1_0_and_2_0_0_false(){
        ComparableVersion version = new ComparableVersion("2.1.0");
        boolean result = version.isNewerThan("2.0.0");
        assertTrue(result);
    }

    @Test
    public void compareSemVerIsNewer_2_0_0_and_1_0_0_false(){
        ComparableVersion version = new ComparableVersion("2.0.0");
        boolean result = version.isNewerThan("1.0.0");
        assertTrue(result);
    }

    @Test
    public void compareSemVerIsNewer_1_0_0_and_1_0_0_false(){
        ComparableVersion version = new ComparableVersion("1.0.0");
        boolean result = version.isNewerThan("1.0.0");
        assertFalse(result);
    }

    @Test
    public void compareSemVerIsNewer_1_0_0_a_and_1_0_0_b_false(){
        ComparableVersion version = new ComparableVersion("1.0.0-a");
        boolean result = version.isNewerThan("1.0.0-b");
        assertFalse(result);
    }

    @Test
    public void compareSemVerIsNewer_1_0_0_a_and_1_0_0_a_false(){
        ComparableVersion version = new ComparableVersion("1.0.0-a");
        boolean result = version.isNewerThan("1.0.0-a");
        assertFalse(result);
    }

    @Test
    public void compareSemVer_1_0_0_a_and_1_0_0_b_false(){
        ComparableVersion version = new ComparableVersion("1.0.0-a");
        boolean result = version.isOlderThan("1.0.0-b");
        assertTrue(result);
    }

    @Test
    public void compareSemVer_1_0_0_a_and_1_0_0_a_false(){
        ComparableVersion version = new ComparableVersion("1.0.0-a");
        boolean result = version.isOlderThan("1.0.0-a");
        assertFalse(result);
    }
    @Test
    public void compareSemVerIsNewer_1_0_1_and_1_0_0_a_false(){
        ComparableVersion version = new ComparableVersion("1.0.1");
        boolean result = version.isNewerThan("1.0.0-a");
        assertTrue(result);
    }

    @Test
    public void compareSemVer_1_0_1_and_1_0_0_a_false(){
        ComparableVersion version = new ComparableVersion("1.0.1");
        boolean result = version.isOlderThan("1.0.0-a");
        assertFalse(result);
    }
}
