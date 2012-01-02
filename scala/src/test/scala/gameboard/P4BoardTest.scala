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

    "refuse moves in column > 6" in {
      val board = new P4Board(emptyColumns, RED)

      board.play(RED, 7) must throwA[IllegalArgumentException]
    }

    "refuse moves in columns already full" in {
      val board = new P4Board(List(List(), List(), List(), List.fill(6)(RED), List(), List(), List()), RED)

      board.play(RED, 3) must throwA[IllegalArgumentException]
    }

    "detect when player wins on columns" in {
      val board = new P4Board(List(List(), List(), List(YELLOW), List.fill(4)(RED), List(YELLOW), List(YELLOW), List()), RED)

      board.gameEnded must_== true
      board.winner must_== Some(RED)

      val board2 = new P4Board(List(List(), List(), List(YELLOW), List.fill(3)(RED):+ YELLOW, List(YELLOW), List(YELLOW), List()), RED)

      board2.gameEnded must_== false
      board2.winner must_== None
    }

    "detect when player wins on rows" in {
      val board = new P4Board(List(List(), List(), List(YELLOW), List(YELLOW), List(YELLOW), List(YELLOW), List()), RED)

      board.gameEnded must_== true
      board.winner must_== Some(YELLOW)

      val board2 = new P4Board(List(List(), List(), List(YELLOW), List(RED), List(YELLOW), List(YELLOW), List()), RED)

      board2.gameEnded must_== false
      board2.winner must_== None
    }

    "detect when player wins on Diagonal1" in {
      val board = new P4Board(List(List(), List(RED), List(YELLOW, RED), List(YELLOW, RED, RED), List(RED, YELLOW, YELLOW, RED), List(), List()), RED)

      board.gameEnded must_== true
      board.winner must_== Some(RED)

      val board2 = new P4Board(List(List(), List(RED), List(YELLOW, RED), List(YELLOW, RED, YELLOW), List(RED, YELLOW, YELLOW, RED), List(), List()), RED)

      board2.gameEnded must_== false
      board2.winner must_== None
    }

    "detect when player wins on Diagonal2" in {
      val board = new P4Board(List(List(), List(RED, YELLOW, YELLOW, RED), List(YELLOW, RED, RED),List(YELLOW, RED),List(RED),   List(), List()), RED)

      board.gameEnded must_== true
      board.winner must_== Some(RED)

      val board2 = new P4Board(List(List(), List(RED, YELLOW, YELLOW, YELLOW), List(YELLOW, RED, RED),List(YELLOW, RED),List(RED),   List(), List()), RED)

      board2.gameEnded must_== false
      board2.winner must_== None
    }
    
    "refuse move when game is ended" in {
      val board = new P4Board(List(List(), List(RED, YELLOW, YELLOW, RED), List(YELLOW, RED, RED),List(YELLOW, RED),List(RED),   List(), List()), RED)
      
      board.play(RED, 0) must throwA[IllegalArgumentException]
    }

  }
}
