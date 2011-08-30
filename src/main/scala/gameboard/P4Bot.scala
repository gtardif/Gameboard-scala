package gameboard

class P4Bot(val side: Side.Side) extends Player {
  def updateBoard(board: P4Board, game: P4Game) {
    if (board.player == side) {
      game.play(3, this)
    }
  }

  def newMessage(message: String) {
    println("Bot received " + message)
  }
}