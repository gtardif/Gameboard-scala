package gameboard

import org.specs.mock.Mockito
import org.specs.runner.JUnit
import gameboard.Side._
import org.specs.SpecificationWithJUnit
import org.mockito.Mockito._

class P4BotTest extends SpecificationWithJUnit with Mockito {
  val mockBoard = mock[P4Board]
  mockBoard.player returns RED
  mockBoard.columns returns P4Board.emptyColumns
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

    "never play full column" in {
      bot.updateBoard(new P4Board(List(List(), List(), List(), List(RED, YELLOW, RED, YELLOW, RED, YELLOW), List(), List(), List()), RED), mockGame)

      there was one(mockGame).play(0, bot)
    }

    "block columns where opponent can win" in {
      bot.updateBoard(new P4Board(List(List(), List(), List(), List(RED, RED), List(RED, YELLOW, YELLOW, YELLOW), List(), List()), RED), mockGame)

      there was one(mockGame).play(4, bot)
    }

    "win on column if possible in any case" in {
      bot.updateBoard(new P4Board(List(List(), List(YELLOW, YELLOW, YELLOW), List(), List(RED, RED, RED), List(), List(), List()), RED), mockGame)

      there was one(mockGame).play(3, bot)
    }

    "block raws where opponent can win" in {
      bot.updateBoard(new P4Board(List(List(RED, RED), List(), List(), List(YELLOW), List(YELLOW), List(YELLOW), List(RED)), RED), mockGame)

      there was one(mockGame).play(2, bot)

      bot.updateBoard(new P4Board(List(List(RED, RED), List(YELLOW), List(YELLOW), List(), List(YELLOW), List(YELLOW), List()), RED), mockGame)

      there was one(mockGame).play(3, bot)
    }

    "win on raw if possible in any case" in {
      bot.updateBoard(new P4Board(List(List(YELLOW), List(YELLOW, YELLOW), List(YELLOW, YELLOW), List(YELLOW, RED), List(RED), List(RED), List()), RED), mockGame)

      there was one(mockGame).play(6, bot)
    }
  }
}