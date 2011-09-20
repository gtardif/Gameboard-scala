package gameboard.comet

import net.liftweb._
import http._
import util._
import Helpers._
import net.liftweb.common.Full
import scala.xml.NodeSeq

class GameList extends CometActor with CometListener {
  var games : Iterable[GameServer] = List()
  
  def registerWith = GameServer

  override def lowPriority = {
    case newGames: Iterable[GameServer] => games = newGames; reRender()
  }

  override def lifespan = Full(1 minutes)

  def render = "li * " #> games.map(game => "P4 : " + game.name)
}