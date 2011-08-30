package gameboard

import org.specs.mock.Mockito
import org.specs.runner.JUnit
import gameboard.Side._
import org.specs.SpecificationWithJUnit

class P4BotTest extends SpecificationWithJUnit with Mockito {
  val mockBoard = mock[P4Board]
  mockBoard.player returns RED
  mockBoard.columns returns List()
  val mockGame = mock[P4Game]

  val bot = new P4Bot(RED)

  "P4 bot" should {
    "start game in middle column" in {

      bot.updateBoard(mockBoard, mockGame)

      there was one(mockGame).play(3, bot)
    }

    "play only when it is its turn" in {
      mockBoard.player returns YELLOW

      bot.updateBoard(mockBoard, mockGame)

      there was no(mockGame).play(3, bot)
    }

    "block columns where opponent can win" in {
      val board = new P4Board(List(List(), List(),List(), List(RED, RED), List(YELLOW, YELLOW, YELLOW), List(), List()), RED)
      
      bot.updateBoard(board, mockGame)
      
      there was one(mockGame).play(4, bot)
    }
  }
}