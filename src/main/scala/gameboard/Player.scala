package gameboard
import scala.actors.Actor

trait Player extends Actor {
  val side: Side.Side
  
  def updateBoard(board: P4Board, game : P4Game): Unit
  def newMessage(message:String): Unit
  
 
  def act() {
    receive {
      case (board: P4Board, game: P4Game) => {
        updateBoard(board, game)
        act()
      }
      case message:String => {
        newMessage(message)
        act()
      }
    }
  }

}