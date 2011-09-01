package gameboard

import Side._

class P4Bot(val side: Side.Side) extends Player {
  def updateBoard(board: P4Board, game: P4Game) {
    if (board.player != side) {
      return
    }

    if (board.columns == P4Board.emptyColumns) {
      game.play(3, this)
      return
    }

    val indexedColumnScores = (0 to board.columns.size - 1) map { (_, board.columns) } map { i =>
      val (index, columns) = i
      (score(index, board), index)
    }

    val scoreWithColumnOrderedPerScore = indexedColumnScores sortWith ((score1, score2) => score1._1 > score2._1)
    game.play(scoreWithColumnOrderedPerScore.head._2, this)
  }

  private def score(index: Int, board: P4Board): Int = {
    val myScore = score(index, board.columns, side)
    val otherScore = score(index, board.columns, other(side))

    return myScore + otherScore
  }

  private def score(index: Int, columns: List[List[Side.Side]], side: Side): Int = {
    val board = new P4Board(columns, side)
    if (board.columns(index).size == 6) return -1

    val nextColumns = board.play(board.player, index).columns
    val column = nextColumns(index)

    val you = other(board.player)
    val columnScore = column.reverse match {
      case (board.player :: board.player :: board.player :: board.player :: _) => 6
      case (column) if column.size > 6 => -1
      case (_) => 0
    }

    val height = column.size - 1
    val neighbourColumns = if (index > 3) nextColumns.drop(index - 3) else nextColumns.dropRight(column.size - 1 - index - 3)
    val rawLine = neighbourColumns map { column => if (column.size > height) Some(column(height)) else Some() }

    val rawScore = if (rawLine.containsSlice(List.fill(4)(Some(board.player)))) 6 else 0

    return columnScore + rawScore
  }

  def newMessage(message: String) {
    println("Bot received " + message)
  }
}