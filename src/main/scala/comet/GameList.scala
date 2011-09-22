package gameboard.comet

import net.liftweb._
import http._
import util._
import Helpers._
import net.liftweb.common.Full
import scala.xml.NodeSeq
import net.liftweb.http.js.JsCmds.Alert

class GameList extends CometActor with CometListener {
  var games: Iterable[GameServer] = List()
  
  def registerWith = GameServer
  override def lifespan = Full(1 minutes)

  override def lowPriority = {
    case newGames: Iterable[GameServer] => games = newGames; reRender()
  }

  def render = "li * " #>  games.map(game => <a href={"standaloneGame?name=" + game.name }>P4 : {game.name}</a>) &
		  	"#newGame [onClick]" #> "newGame(prompt('name for the new Game'))"
}