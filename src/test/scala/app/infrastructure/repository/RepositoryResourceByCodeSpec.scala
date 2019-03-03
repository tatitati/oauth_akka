package test.app.infrastructure.repository

import app.domain.thirdstore.resourcestore.ResourceByCode
import app.infrastructure.repository.RepositoryResourceByCode
import com.redis.RedisClient
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite}
import test.builders.BuildResourceByCode

class RepositoryResourceByCodeSpec extends FunSuite with BeforeAndAfterEach with BeforeAndAfterAll {

  val redisClient = new RedisClient("localhost", 6379)
  val repo = new RepositoryResourceByCode(redisClient)

  test("Can persist in redis") {
      val givenResourceByCode = BuildResourceByCode.any()

      assert(repo.save(givenResourceByCode) === true)
  }

  test("Return None when reading an unexisting code") {
    val result = repo.read("anycode_expired")

    assert(result === None)
  }

  test("Return Some when reading an existing code") {
    val givenResourceByCode = BuildResourceByCode.any()

    repo.save(givenResourceByCode, 1)

    val result = repo.read(givenResourceByCode.exportMemento().code)

    assert(result.isInstanceOf[Some[ResourceByCode]])
    assert(result.get.exportMemento() === givenResourceByCode.exportMemento())
  }

  override def beforeAll() {
    redisClient.flushall
    redisClient.flushdb
  }

  override def afterEach() {
    redisClient.flushall
    redisClient.flushdb
  }
}