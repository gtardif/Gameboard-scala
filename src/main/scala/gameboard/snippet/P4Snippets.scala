package gameboard.snippet

import net.liftweb._
import net.liftweb.util.Helpers._
import http._
import js._
import JsCmds._
import JE._
import scala.xml.NodeSeq

import gameboard.comet.GameServer

object P4Snippets {
  def ajaxForm = {
    var column = ""
      
    val game = GameServer.lastCreated.game
    val gameId = GameServer.lastCreated.id.toString()

    def process() {
      println ("process " + column + ", " + gameId)
      game.play(column.toInt, game.players(0))
    }

      "#column" #> (SHtml.text(column, column = _) ++ SHtml.hidden(process))
  }
  
}
