package gameboard

import org.specs.SpecificationWithJUnit
import org.specs.mock.Mockito
import org.specs.runner.JUnit
import gameboard.Side._

class P4BoardTest extends SpecificationWithJUnit with Mockito {
  private val emptyColumns = List.fill(7)(List.empty)

  "P4Board" should {
    "change player after each turn" in {
      val board = new P4Board(emptyColumns, RED)

      val board1 = board.play(RED, 3)
      board1.player must_== YELLOW

      val board2 = board1.play(YELLOW, 3)
      board2.player must_== RED
    }

    "refuse move from wrong player" in {
      val board = new P4Board(emptyColumns, RED)

      board.play(YELLOW, 3) must throwA[IllegalArgumentException]
    }

    "add pawn in specified column" in {
      val board = new P4Board(emptyColumns, RED)

      val board1 = board.play(RED, 3)

      board1.columns must_== List(List(), List(), List(), List(RED), List(), List(), List())

      val board2 = board1.play(YELLOW, 3)

      board2.columns must_== List(List(), List(), List(), List(RED, YELLOW), List(), List(), List())
    }

    "refuse moves in column > 7" in {
      val board = new P4Board(emptyColumns, RED)

      board.play(RED, 8) must throwA[IllegalArgumentException]
    }

    "refuse moves in columns already full" in {
      val columns = List(List(), List(), List(), List.fill(6)(RED), List(), List(), List())

      val board = new P4Board(columns, RED)

      board.play(RED, 3) must throwA[IllegalArgumentException]
    }
  }
}
