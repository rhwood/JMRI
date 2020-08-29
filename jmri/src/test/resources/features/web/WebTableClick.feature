@webtest @webtable
Feature: JMRI Web Table Click

Scenario Outline: Web Table requests with click testing
   Given I am using <browser>
   And panel <panel> is loaded
   And  <table> is visible
   And item <item> with entry <column> has state <before>
   When <column> for <item> in <table> is clicked
   Then item <item> with entry <column> has state <after>

   @firefox 
   Examples: Firefox Table Click Tests
   | browser | panel | table | item | column | before | after |
   | firefox | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Turnouts | IT0 | state | closed | thrown | 
   | firefox | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Turnouts | IT1 | state | unknown | thrown |
   | firefox | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Lights | IL0 | state | off | on |
   | firefox | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Sensors | ISCLOCKRUNNING | state | active | inactive |
   | firefox | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Memories | IMRATEFACTOR | value | 1.0 | 1.0 |
   | firefox | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Signal Heads | IH8 | lit | true | true |

   @chrome
   Examples: Chrome Table Click Tests
   | browser | panel | table | item | column | before | after |
   | chrome | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Turnouts | IT2 | state | closed | thrown | 
   | chrome | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Turnouts | IT4 | inverted | false | false |
   | chrome | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Lights | IL0 | state | on | off |
   | chrome | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Sensors | ISCLOCKRUNNING | state | active | inactive |
   | chrome | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Memories | IMRATEFACTOR | value | 1.0 | 1.0 | 
   | chrome | jmri/src/test/java/jmri/jmrit/display/configurexml/load/OneOfEach.xml | Signal Heads | IH8 | lit | true | true | 
   
