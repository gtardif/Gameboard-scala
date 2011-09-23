package gameboard.comet

import gameboard._
import net.liftweb.http.ListenerManager
import net.liftweb.actor.LiftActor

class WebPlayer(val side: Side.Side) extends Player {
  private var msgs: List[AnyRef] = List()
  val updater = new Updater()

  override def updateBoard(board: P4Board, game: P4Game) {
    if (game.moves.isEmpty) return
    val lastMove = game.moves.last
    msgs = lastMove +: msgs;
    updater.update()
  }

  override def newMessage(message: String) {
    msgs = message +: msgs;
    updater.update()
  }

  class Updater extends LiftActor with ListenerManager {
    def createUpdate = msgs
    def update() = updateListeners()
  }
}