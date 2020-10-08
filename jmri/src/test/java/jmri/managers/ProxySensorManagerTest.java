// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.managers;

import java.beans.PropertyChangeListener;
import java.util.*;

import jmri.*;
import jmri.jmrix.internal.InternalSensorManager;
import jmri.jmrix.internal.InternalSystemConnectionMemo;
import jmri.util.JUnitAppender;
import jmri.util.JUnitUtil;

import org.junit.Assert;
import org.junit.jupiter.api.*;

/**
 * Test the ProxySensorManager
 *
 * @author Bob Jacobsen 2003, 2006, 2008, 2014
 */
public class ProxySensorManagerTest implements PropertyChangeListener {

    protected ProxySensorManager l = null; // holds objects under test

    @Test
    public void testDispose() {
        l.dispose();  // all we're really doing here is making sure the method exists
    }

    @Test
    public void testPutGetJ() {
        // create
        Sensor tj = l.newSensor("JS1", "mine");
        // check
        Assert.assertTrue("real object returned ", tj != null);
        Assert.assertTrue("user name correct ", tj == l.getByUserName("mine"));
        Assert.assertTrue("system name correct ", tj == l.getBySystemName("JS1"));
    }

    @Test
    public void testSensorNameCase() {
        Assert.assertEquals(0, l.getObjectCount());
        // create
        Sensor t = l.provideSensor("IS:XYZ");
        Assert.assertNotEquals(t, l.provideSensor("IS:xyz"));  // upper case and lower case are different objects
        // check
        Assert.assertTrue("real object returned ", t != null);
        Assert.assertEquals("IS:XYZ", t.getSystemName());  // we force upper
        Assert.assertTrue("system name correct ", t == l.getBySystemName("IS:XYZ"));
        Assert.assertEquals(2, l.getObjectCount());
        Assert.assertEquals(2, l.getNamedBeanSet().size());
        // test providing same name as existing sensor does not create new sensor
        l.provideSensor("IS:XYZ");
        Assert.assertEquals(2, l.getObjectCount());
        Assert.assertEquals(2, l.getNamedBeanSet().size());
    }

    @Test
    public void testPutGetI() {
        // create
        Sensor ti = l.newSensor("IS1", "mine");
        // check
        Assert.assertTrue("real object returned ", ti != null);
        Assert.assertTrue("user name correct ", ti == l.getByUserName("mine"));
        Assert.assertTrue("system name correct ", ti == l.getBySystemName("IS1"));
    }

    @Test
    public void testPutGetK() {
        // create
        Sensor tk = l.newSensor("KS1", "mine");
        // check
        Assert.assertTrue("real object returned ", tk != null);
        Assert.assertTrue("user name correct ", tk == l.getByUserName("mine"));
        Assert.assertTrue("system name correct ", tk == l.getBySystemName("KS1"));
    }

    @Test
    public void testDefaultSystemName() {
        // create
        Sensor t = l.provideSensor("9");
        // check
        Assert.assertTrue("real object returned", t != null);
        Assert.assertEquals("system name correct", "JS9", t.getSystemName());
        Assert.assertEquals("can find by name", t, l.getBySystemName("JS9"));
    }

    @Test
    public void testProvideFailure() {
        Assert.assertThrows(IllegalArgumentException.class, () -> l.provideSensor(""));
        JUnitAppender.assertErrorMessage("Invalid system name for Sensor: System name must start with \"" + l.getSystemNamePrefix() + "\".");
    }

    @Test
    public void testSingleObject() {
        // test that you always get the same representation
        Sensor t1 = l.newSensor("JS1", "mine");
        Assert.assertTrue("t1 real object returned ", t1 != null);
        Assert.assertEquals("same by user ", t1, l.getByUserName("mine"));
        Assert.assertEquals("same by system ", t1, l.getBySystemName("JS1"));

        Sensor t2 = l.newSensor("JS1", "mine");
        Assert.assertTrue("t2 real object returned ", t2 != null);
        // check
        Assert.assertTrue("same new ", t1 == t2);
    }

    @Test
    public void testMisses() {
        // try to get nonexistant objects
        Assert.assertTrue(null == l.getByUserName("foo"));
        Assert.assertTrue(null == l.getBySystemName("bar"));
    }

    @Test
    public void testUpperLower() {  // verify that names are case sensitive
        Sensor t = l.provideSensor("JS1ABC");  // internal will always accept that name
        String name = t.getSystemName();
        
        int prefixLength = l.getSystemPrefix().length()+1;     // 1 for type letter
        String lowerName = name.substring(0,prefixLength)+name.substring(prefixLength, name.length()).toLowerCase();
        
        Assert.assertNotEquals(t, l.getSensor(lowerName));
    }

    @Test
    public void testRename() {
        // get
        Sensor t1 = l.newSensor("JS1", "before");
        Assert.assertNotNull("t1 real object ", t1);
        t1.setUserName("after");
        Sensor t2 = l.getByUserName("after");
        Assert.assertEquals("same object", t1, t2);
        Assert.assertEquals("no old object", null, l.getByUserName("before"));
    }

    @Test
    public void testTwoNames() {
        Sensor jl212 = l.provideSensor("JS212");
        Sensor jl211 = l.provideSensor("JS211");

        Assert.assertNotNull(jl212);
        Assert.assertNotNull(jl211);
        Assert.assertTrue(jl212 != jl211);
    }

    @Test
    public void testDefaultNotInternal() {
        Sensor lut = l.provideSensor("211");

        Assert.assertNotNull(lut);
        Assert.assertEquals("JS211", lut.getSystemName());
    }

    @Test
    public void testProvideUser() {
        Sensor l1 = l.provideSensor("211");
        l1.setUserName("user 1");
        Sensor l2 = l.provideSensor("user 1");
        Sensor l3 = l.getSensor("user 1");

        Assert.assertNotNull(l1);
        Assert.assertNotNull(l2);
        Assert.assertNotNull(l3);
        Assert.assertEquals(l1, l2);
        Assert.assertEquals(l3, l2);
        Assert.assertEquals(l1, l3);

        Sensor l4 = l.getSensor("JLuser 1");
        Assert.assertNull(l4);
    }

    // the following methods test code in Manager and AbstractManager,
    // but they need a concrete implementation to do it, hence are here.
    
    @Test
    public void testAddTracking() {
        Sensor s1 = l.provideSensor("IS1");
        s1.setUserName("Sensor 1");
        
        l.addPropertyChangeListener("length", this);
        l.addPropertyChangeListener("DisplayListName", this);
        
        // add an item
        Sensor s2 = l.provideSensor("IS2");

        // property listener should have been immediately invoked
        Assert.assertEquals("propertyListenerCount", 1, propertyListenerCount);
        Assert.assertEquals("last call", "length", propertyListenerLast);

        s2.setUserName("Sensor 2");

        Assert.assertEquals("propertyListenerCount", 2, propertyListenerCount);
        Assert.assertEquals("last call", "DisplayListName", propertyListenerLast);

        // add an item
        Sensor s3 = l.newSensor("IS3", "Sensor 3");

        // property listener should have been immediately invoked
        Assert.assertEquals("propertyListenerCount", 3, propertyListenerCount);
        Assert.assertEquals("last call", "length", propertyListenerLast);

        // can add a manager and still get notifications
        l.addManager(new InternalSensorManager(new InternalSystemConnectionMemo("Z", "Zulu")));
        Sensor s4 = l.provideSensor("ZS2");

        // property listener should have been immediately invoked
        Assert.assertEquals("propertyListenerCount", 5, propertyListenerCount);
        Assert.assertEquals("last call", "length", propertyListenerLast);
        
    }

    @Test
    public void testRemoveTrackingI() {
        
        Sensor s1 = l.provideSensor("IS1");
        s1.setUserName("Sensor 1");
        Sensor s2 = l.provideSensor("IS2");
        l.provideSensor("IS3");
        
        l.deregister(s2);
    }

    @Test
    public void testRemoveTrackingJ() {
        
        l.provideSensor("IS10");
        l.provideSensor("IS11");
        
        Sensor s1 = l.provideSensor("JS1");
        s1.setUserName("Sensor 1");
        Sensor s2 = l.provideSensor("JS2");
        l.provideSensor("JS3");
        
        l.deregister(s2);
    }

    @Test
    public void testGetObjectCount() {
        Assert.assertEquals(0, l.getObjectCount());

        l.provideSensor("IS10");
        Assert.assertEquals(1, l.getObjectCount());

        l.provideSensor("JS1");
        Assert.assertEquals(2, l.getObjectCount());
        
        l.provideSensor("IS11");
        Assert.assertEquals(3, l.getObjectCount());

        Sensor s2 = l.provideSensor("JS2");
        l.provideSensor("JS3");
        Assert.assertEquals(5, l.getObjectCount());
        
        l.deregister(s2);
        Assert.assertEquals(4, l.getObjectCount());
    }

    @Test
    public void testOrderVsSorted() {
        Sensor s4 = l.provideSensor("IS4");
        Sensor s2 = l.provideSensor("IS2");
        
        List<String> sortedList = l.getSystemNameList();
        SortedSet<Sensor> beanSet = l.getNamedBeanSet();
        
        Assert.assertEquals("sorted list length", 2, sortedList.size());
        Assert.assertEquals("sorted list 1st", "IS2", sortedList.get(0));
        Assert.assertEquals("sorted list 2nd", "IS4", sortedList.get(1));

        Assert.assertEquals("bean set length", 2, beanSet.size());
        Iterator<Sensor> iter = beanSet.iterator();
        Assert.assertEquals("bean set 1st", s2, iter.next());
        Assert.assertEquals("bean set 2nd", s4, iter.next());
        
        // add and test (non) liveness
        Sensor s3 = l.provideSensor("IS3");
        Sensor s1 = l.provideSensor("IS1");

        Assert.assertEquals("sorted list length", 2, sortedList.size());
        Assert.assertEquals("sorted list 1st", "IS2", sortedList.get(0));
        Assert.assertEquals("sorted list 2nd", "IS4", sortedList.get(1));

        Assert.assertEquals("bean set length", 4, beanSet.size());
        iter = beanSet.iterator();
        Assert.assertEquals("bean set 1st", s1, iter.next());
        Assert.assertEquals("bean set 2nd", s2, iter.next());
        Assert.assertEquals("bean set 3rd", s3, iter.next());
        Assert.assertEquals("bean set 4th", s4, iter.next());

        // update and test update
        sortedList = l.getSystemNameList();
        beanSet = l.getNamedBeanSet();
        
        Assert.assertEquals("sorted list length", 4, sortedList.size());
        Assert.assertEquals("sorted list 1st", "IS1", sortedList.get(0));
        Assert.assertEquals("sorted list 2nd", "IS2", sortedList.get(1));
        Assert.assertEquals("sorted list 3rd", "IS3", sortedList.get(2));
        Assert.assertEquals("sorted list 4th", "IS4", sortedList.get(3));

        Assert.assertEquals("bean set length", 4, beanSet.size());
        iter = beanSet.iterator();
        Assert.assertEquals("bean set 1st", s1, iter.next());
        Assert.assertEquals("bean set 2nd", s2, iter.next());
        Assert.assertEquals("bean set 3rd", s3, iter.next());
        Assert.assertEquals("bean set 4th", s4, iter.next());

    }

    @Test
    public void testUnmodifiable() {
        Sensor s1 = l.provideSensor("IS1");
        l.provideSensor("IS2");
        
        List<String> nameList = l.getSystemNameList();
        SortedSet<Sensor> beanSet = l.getNamedBeanSet();

        try {
            nameList.add("Foo");
            Assert.fail("nameList should have thrown");
        } catch (UnsupportedOperationException e) { /* this is OK */}

        try {
            beanSet.add(s1);
            Assert.fail("beanSet should have thrown");
        } catch (UnsupportedOperationException e) { /* this is OK */}

    }

    // check how proxy is integrated with defaults
    @Test
    public void testInstanceManagerIntegration() {
        jmri.util.JUnitUtil.resetInstanceManager();
        Assert.assertNotNull(InstanceManager.getDefault(SensorManager.class));

        jmri.util.JUnitUtil.initInternalSensorManager();

        Assert.assertTrue(InstanceManager.getDefault(SensorManager.class) instanceof ProxySensorManager);

        Assert.assertNotNull(InstanceManager.getDefault(SensorManager.class));
        Assert.assertNotNull(InstanceManager.getDefault(SensorManager.class).provideSensor("IS1"));

        InternalSensorManager m = new InternalSensorManager(new InternalSystemConnectionMemo("J", "Juliet"));
        InstanceManager.setSensorManager(m);

        Assert.assertNotNull(InstanceManager.getDefault(SensorManager.class).provideSensor("JS1"));
        Assert.assertNotNull(InstanceManager.getDefault(SensorManager.class).provideSensor("IS2"));
    }

    // Property listen & audit methods
    static protected int propertyListenerCount = 0;
    static protected String propertyListenerLast = null;

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent e) {
        propertyListenerCount++;
        propertyListenerLast = e.getPropertyName();
    }

    @BeforeEach
    public void setUp() {
        JUnitUtil.setUp();
        // create and register the manager object
        l = new ProxySensorManager();
        // initially has three systems: IS, JS, KS
        l.addManager(new InternalSensorManager(new InternalSystemConnectionMemo("J", "Juliet")));
        l.addManager(new InternalSensorManager(new InternalSystemConnectionMemo("I", "India"))); // not in alpha order to make it exciting
        l.addManager(new InternalSensorManager(new InternalSystemConnectionMemo("K", "Kilo")));

        jmri.InstanceManager.setSensorManager(l);
        
        propertyListenerCount = 0;
        propertyListenerLast = null;
    }

    @AfterEach
    public void tearDown() {
        JUnitUtil.tearDown();
    }

}