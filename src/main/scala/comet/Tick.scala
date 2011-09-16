package gameboard.comet

import net.liftweb._
import http._
import util._
import common._
import Helpers._
import scala.xml.NodeSeq

class Tick extends CometActor {
  private def pinger() {
    ActorPing.schedule(this, "Hello", 30 seconds)
  }

  private var lastData = "None"

  pinger()
  println("I was just instantiated: "+name+" uid "+uniqueId)
  
  override def initCometActor(theSession: LiftSession,
                               theType: Box[String],
                               name: Box[String],
                               defaultHtml: NodeSeq,
                               attributes: Map[String, String]) = {
    super.initCometActor(theSession, theType, name, defaultHtml, attributes);
    println("init name = " + name)
  }

  override def lifespan = Full(1 minutes)
  
  override def lowPriority = {
    case "Hello" => pinger(); reRender()
    case s: String => lastData = s; reRender()
  }

  def render = {
    <div>
    Howdy... my name is {name} id is {uniqueId} 
    at {new java.util.Date}.  I
    will update every 30 seconds.
    <div>
    My last message was: {lastData}
    </div>
    </div>
  }
}
