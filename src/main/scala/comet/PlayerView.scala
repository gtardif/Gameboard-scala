package gameboard.comet

import net.liftweb._
import http._
import util._
import Helpers._

class PlayerView extends CometActor with CometListener {
  private var msgs: Vector[String] = Vector() // private state

  def registerWith = GameServer

  override def lowPriority = {
    case v: Vector[String] => msgs = v; reRender()
  }

  def render = "li *" #> msgs & ClearClearable
}
