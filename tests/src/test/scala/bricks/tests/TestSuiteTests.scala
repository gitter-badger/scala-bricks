package bricks
package tests

class StdTestTests extends TestSuite {

  test("StdTest minSuccessful") {
    val min = if (Platform.isJvm) 100 else 10
    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("StdTest maxDiscardedFactor") {
    val max = if (Platform.isJvm) 5.0 else 50.0
    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }
}

class WellTestedTests extends TestSuite with WellTested {

  test("WellTested minSuccessful") {
    val min = if (Platform.isJvm) 10 else 1
    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("WellTested maxDiscardedFactor") {
    val max = if (Platform.isJvm) 5.0 else 50.0
    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }
}

import org.scalactic.anyvals.{PosZDouble, PosInt}

class WellTestedCustomPlatformTests extends TestSuite with WellTested {

 override def minSuccessful: PosInt =
    if (Platform.isJvm) PosInt(50)
    else PosInt(5)

  override def maxDiscardedFactor: PosZDouble =
    if (Platform.isJvm) PosZDouble(2.0)
    else PosZDouble(20.0)

  override def testType: TestModifier =
    if (Platform.isJvm) TestModifier(0.1, 0.5)
    else TestModifier(0.1, 0.5)

  test("WellTested CustomPlatform  minSuccessful") {
    val min = if (Platform.isJvm) 5 else 1
    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("WellTested CustomPlatform maxDiscardedFactor") {
    val max = if (Platform.isJvm) 1.0 else 10.0
    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }
}

trait MySetup extends TestSettings with StdTest with TestNotifications {

  override  def shouldNotify: Boolean = false

  override def minSuccessful: PosInt =
    if (Platform.isJvm) PosInt(50)
    else PosInt(5)

  override def maxDiscardedFactor: PosZDouble =
    if (Platform.isJvm) PosZDouble(2.0)
    else PosZDouble(20.0)

  override def testType: TestModifier =
    if (Platform.isJvm) TestModifier(0.1, 0.5)
    else TestModifier(0.1, 0.5)
}

class MySetupTests extends TestSuite with MySetup {

  test("MySetup  minSuccessful") {
    val min = if (Platform.isJvm) 5 else 1
    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("MySetup maxDiscardedFactor") {
    val max = if (Platform.isJvm) 1.0 else 10.0
    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }
}

class StressTestModeTest extends TestSuite {

  override def getTestMode: TestModifier = StressTestMode.testMode

  test("StressTestMode StdTest minSuccessful") {
    val min = if (Platform.isJvm) 100000 else 1000
    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("StressTestMode StdTest maxDiscardedFactor") {
    val max = if (Platform.isJvm) 500.0 else 500.0
    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }
}

trait MyStressSetup extends TestSettings with StdTest with TestNotifications {

  override def minSuccessful: PosInt =
    if (Platform.isJvm) PosInt(45)
    else PosInt(7)

  override def maxDiscardedFactor: PosZDouble =
    if (Platform.isJvm) PosZDouble(4.0)
    else PosZDouble(14.0)

  override def testType: TestModifier =
    if (Platform.isJvm) TestModifier(0.4, 0.7)
    else TestModifier(0.3, 0.3)
}

class MyStressTestModeTest extends TestSuite with MyStressSetup {

  override def getTestMode: TestModifier = if (Platform.isJvm) TestModifier(888, 88.0)
    else TestModifier(77, 17.0)

  test("MyStressTestMode minSuccessful") {
    val min =
      if (Platform.isJvm) (45 * 888 * 0.4).toInt
      else (7 * 77 * .3).toInt

    assert(generatorDrivenConfig.minSuccessful.value == min)
  }

  test("MyStressTestMode maxDiscardedFactor") {
    val max =
      if (Platform.isJvm) 4 * 88 * 0.7
      else 14 * 17 * 0.3

    assert(generatorDrivenConfig.maxDiscardedFactor.value == max)
  }
}
