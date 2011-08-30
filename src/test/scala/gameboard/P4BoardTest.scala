package gameboard

import org.specs.SpecificationWithJUnit
import org.specs.mock.Mockito
import org.specs.runner.JUnit

class P4BpardTest extends SpecificationWithJUnit with Mockito {
  
   "'hello world' has 11 characters" in {
     "hello world".size must_== 11
  }
  
  "mockito" in {
    val m = mock[java.util.List[String]] // a concrete class would be mocked with: mock(new java.util.LinkedList[String])

    // stub a method call with a return value
    m.get(0) returns "one"

    // call the method
    m.get(0)

    // verify that the call happened, this is an expectation which will throw a FailureException if that is not the case
    there was one(m).get(0)

    // we can also check that another call did not occur
    there was no(m).get(1)

  }

}
