package gameboard

import scala.actors.Actor

class HumanPlayer(val side: Side.Side) extends Player {
  private val frame : MainFrame = new MainFrame(this, P4Board.empty)
  
  def updateBoard(board: P4Board, game : P4Game) {
    frame.boardPanel.updateBoard(board, game)
  }
  
  def newMessage(message:String) {
    println(message)
  }
}