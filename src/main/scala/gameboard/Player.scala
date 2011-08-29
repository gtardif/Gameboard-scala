package gameboard
import scala.actors.Actor

trait Player extends Actor {
  val side: Side.Side
  //TODO remove this var
  protected var game: P4Game = null
  
  def updateBoard(board: P4Board): Unit
  def newMessage(message:String): Unit
  
  final def play(column : Int) {
    game!(column, this)
  }

  def act() {
    receive {
      case (board: P4Board, game: P4Game) => {
        this.game = game
        updateBoard(board)
        act()
      }
      case message:String => {
        newMessage(message)
        act()
      }
    }
  }

}