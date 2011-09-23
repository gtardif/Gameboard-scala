package gameboard.comet

import net.liftweb._
import http._
import util._
import Helpers._
import actor._
import net.liftweb.common.Full
import scala.xml.NodeSeq
import net.liftweb.http.js.JsCmds.Alert
import gameboard._

class GameList extends CometActor with CometListener {
  var games: Iterable[GameStarter] = List()
  
  def registerWith = LiftCometGameServer
  override def lifespan = Full(1 minutes)

  override def lowPriority = {
    case newGames: Iterable[GameStarter] => games = newGames; reRender()
  }

  def render = "li * " #>  games.map(game => <a href={"standaloneGame?name=" + game.name }>P4 : {game.name}</a>) &
		  	"#newGame [onClick]" #> "newGame(prompt('name for the new Game'))"
}

object LiftCometGameServer extends GameServer with LiftActor with ListenerManager  {
	def createUpdate = {
			games.values
	}
	
	def update = updateListeners
}
