package gameboard

import Side._

class P4Bot(val side: Side.Side) extends Player {
  def updateBoard(board: P4Board, game: P4Game) {
    if (board.gameEnded || board.player != side) {
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

    return 2 * myScore + otherScore
  }

  private def score(index: Int, columns: List[List[Side.Side]], side: Side): Int = {
    val board = new P4Board(columns, side)
    if (board.columns(index).size == 6) return -1

    val nextColumns = board.play(board.player, index).columns

    val playedColumn = nextColumns(index)
    val height = playedColumn.size - 1

    val columnScore = score(playedColumn map { (Some(_)) } padTo (6, None), board.player)

    val neighbourColumns = if (index > 3) nextColumns.drop(index - 3) else nextColumns.dropRight(playedColumn.size - 1 - index - 3)
    val rawLine = neighbourColumns map { column => if (column.size > height) Some(column(height)) else None }
    val rawScore = score(rawLine, board.player)

    val diag1Line = (neighbourColumns zipWithIndex) map { columnWithIndex =>
      val (column, colIndex) = columnWithIndex
      val wantedHeight = height - (index - colIndex)
      if (column.size > wantedHeight && wantedHeight >= 0) Some(column(wantedHeight)) else None
    }
    val diag1Score = score(diag1Line, board.player)

    val diag2Line = (neighbourColumns zipWithIndex) map { columnWithIndex =>
      val (column, colIndex) = columnWithIndex
      val wantedHeight = height + (index - colIndex)
      if (column.size > wantedHeight && wantedHeight >= 0) Some(column(wantedHeight)) else None
    }
    val diag2Score = score(diag2Line, board.player)
    
    return columnScore + rawScore + diag1Score + diag2Score
  }

  def newMessage(message: String) {
    println("Bot received " + message)
  }

  private def score(columnLine: List[Option[Side]], side: gameboard.Side.Side) = {
    if (columnLine.containsSlice(List.fill(4)(Some(side)))) 100 else if (columnLine.containsSlice(List.fill(3)(Some(side)))) 10 else 0
  }
}