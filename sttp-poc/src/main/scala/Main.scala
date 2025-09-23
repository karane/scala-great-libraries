import sttp.client3._
import sttp.client3.circe._
import io.circe.generic.auto._
import io.circe.syntax._

import scala.concurrent.duration._

object Main {
  // Case class for JSON decoding (GET example)
  case class Joke(id: Int, `type`: String, setup: String, punchline: String)

  // Case class for JSON encoding (POST example)
  case class PostData(name: String, age: Int)

  def main(args: Array[String]): Unit = {
    val backend = HttpURLConnectionBackend()

    // ---- Example 1: GET request with JSON ----
    println("=== Example 1: GET a Joke from API ===")
    val jokeRequest = basicRequest
      .get(uri"https://official-joke-api.appspot.com/random_joke")
      .response(asJson[Joke])

    val jokeResponse = jokeRequest.send(backend)
    jokeResponse.body match {
      case Right(joke) =>
        println(s"Joke: ${joke.setup} ... ${joke.punchline}")
      case Left(error) =>
        println(s"Error fetching joke: $error")
    }

    // ---- Example 2: POST request with JSON ----
    println("\n=== Example 2: POST JSON to Postman Echo ===")
    val postData = PostData("Alice", 30)

    val postRequest = basicRequest
      .post(uri"https://postman-echo.com/post") // using Postman Echo (more stable)
      .body(postData.asJson)
      .response(asString)

    val postResponse = postRequest.send(backend)
    println(s"POST Response: ${postResponse.body}")

    // ---- Example 3: GET with Headers & Query Params ----
    println("\n=== Example 3: GET with Headers and Query Params ===")
    val queryRequest = basicRequest
      .get(uri"https://postman-echo.com/get?q=scala&limit=5")
      .header("X-Custom-Header", "sttp-demo")
      .response(asString)

    val queryResponse = queryRequest.send(backend)
    println(s"GET with headers/params: ${queryResponse.body}")

    // ---- Example 4: Timeout Handling ----
    println("\n=== Example 4: GET with Timeout ===")
    val timeoutRequest = basicRequest
      .get(uri"https://httpbin.org/delay/3") // 3s delay
      .readTimeout(1.second)                 // timeout after 1s
      .response(asString)

    val timeoutResponse = timeoutRequest.send(backend)
    println(s"Timeout Response: ${timeoutResponse.body}")

    backend.close()
  }
}
