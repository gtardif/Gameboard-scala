 import org.specs.Specification
  import org.specs.mock.Mockito

  object spec extends Specification with Mockito {
    
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
