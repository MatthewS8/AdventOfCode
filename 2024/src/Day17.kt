import kotlin.math.floor
import kotlin.math.pow
import kotlin.properties.Delegates

private typealias Operand = Int

/**
 * function to evaluate a combo operand
 * @param context the context of the machine
 * @return the value of the operand
 */
private fun Operand.eval(context: Context): Long {
    return when (this) {
        in 0..3 -> this.toLong()
        4 -> context.registerA
        5 -> context.registerB
        6 -> context.registerC
        7 -> error("Reserved")
        else -> error("Unknown operation")
    }
}

private class Context {
    var registerA by Delegates.notNull<Long>()
    var registerB by Delegates.notNull<Long>()
    var registerC by Delegates.notNull<Long>()
    var instructionPointer = -1
    var programSize by Delegates.notNull<Int>()
    var out = mutableListOf<Long>()
}

private abstract class Instruction {
    abstract fun execute(operand: Operand, context: Context)
}

private class Adv : Instruction() {
    override fun execute(operand: Operand, context: Context) {
        context.registerA = floor(context.registerA / 2.0.pow(operand.eval(context).toDouble())).toLong()
        context.instructionPointer++
    }
}

private class Bxl : Instruction() {
    override fun execute(operand: Operand, context: Context) {
        context.registerB = context.registerB.xor(operand.toLong())
        context.instructionPointer++
    }
}

private class Bst : Instruction() {
    override fun execute(operand: Operand, context: Context) {
        context.registerB = operand.eval(context).mod(8L)
        context.instructionPointer++
    }
}

private class Jnz : Instruction() {
    override fun execute(operand: Operand, context: Context) {
        if (context.registerA != 0L) {
            if (operand < context.programSize) {
                context.instructionPointer = operand / 2
            } else {
                context.instructionPointer++
            }
        } else {
            context.instructionPointer = context.programSize
        }
    }
}

private class Bxc : Instruction() {
    override fun execute(operand: Operand, context: Context) {
        context.registerB = context.registerB.xor(context.registerC)
        context.instructionPointer++
    }
}

private class Out : Instruction() {
    override fun execute(operand: Operand, context: Context) {
        context.out.add(operand.eval(context).mod(8L))
        context.instructionPointer++
    }
}

private class Bdv : Instruction() {
    override fun execute(operand: Operand, context: Context) {
        context.registerB = floor(context.registerA / 2.0.pow(operand.eval(context).toDouble())).toLong()
        context.instructionPointer++
    }
}

private class Cdv : Instruction() {
    override fun execute(operand: Operand, context: Context) {
        context.registerC = floor(context.registerA / 2.0.pow(operand.eval(context).toDouble())).toLong()
        context.instructionPointer++
    }
}

private class InstructionFactory {
    companion object {
        fun create(opCode: Int): Instruction {
            return when (opCode) {
                0 -> Adv()
                1 -> Bxl()
                2 -> Bst()
                3 -> Jnz()
                4 -> Bxc()
                5 -> Out()
                6 -> Bdv()
                7 -> Cdv()
                else -> throw IllegalArgumentException("Invalid opcode $opCode")
            }
        }
    }
}

private data class InstructionSet(val instruction: Instruction, val operand: Operand)
private class Machine {
    val context = Context()
    lateinit var program: List<InstructionSet>

    fun initRegisters(registersInitialState: List<String>) {
        this.context.registerA = registersInitialState[0].substringAfter("Register A: ").toLong()
        this.context.registerB = registersInitialState[1].substringAfter("Register B: ").toLong()
        this.context.registerC = registersInitialState[2].substringAfter("Register C: ").toLong()
    }

    fun loadProgram(program: String) {
        val code = program.dropWhile { !it.isDigit() }.split(',').chunked(2)
        this.program = code.map { instruction ->
            InstructionSet(InstructionFactory.create(instruction[0].toInt()), instruction[1].toInt())
        }
        this.context.programSize = this.program.size
    }

    fun execute() {
        this.context.instructionPointer = 0
        while (this.context.instructionPointer in (0 until this.context.programSize)) {
            val currInstruction = this.program[this.context.instructionPointer]
            currInstruction.instruction.execute(currInstruction.operand, this.context)
        }
    }

    fun runAndCheck(registryA: Long, expectedOutput: List<Long>): Boolean {
        context.registerA = registryA
        this.context.instructionPointer = 0
        var match = 0
        while (context.instructionPointer in program.indices) {
            val currInstruction = program[context.instructionPointer]
            currInstruction.instruction.execute(currInstruction.operand, context)
            if (currInstruction.instruction is Out && context.out.last() != expectedOutput[match++]) return false
        }
        return true
    }

    fun debug(a: Long, sliceSize: Int, programOpCodes: List<Long>): Long {
        val partialOutput = programOpCodes.take(sliceSize).reversed()
        for (i in 0L..7L) {
            val currRegA = (a shl 3) + i
            if (runAndCheck(currRegA, partialOutput)) {
                if (sliceSize == programOpCodes.size) return currRegA
                val result = debug(currRegA, sliceSize + 1, programOpCodes)
                if (result != 0L) return result
            }
        }
        return 0
    }
}

fun main() {
    fun part1(input: List<String>): String {
        val machine = Machine()

        machine.initRegisters(input.subList(0, 3))
        machine.loadProgram(input[4])
        machine.execute()
        return machine.context.out.joinToString(",")
    }

    fun part2(input: List<String>): Long {
        val machine = Machine()

        machine.initRegisters(input.subList(0, 3))
        machine.loadProgram(input[4])
        val programOpCodes = input[4].substringAfter("Program: ").split(',').map { it.toLong() }
        return machine.debug(0, 1, programOpCodes.reversed())
    }

    // Or read a large test input from the `src/Day17_test.txt` file:
    val testInput = readInput("Day17_test")
    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")

    // Read the input from the `src/Day17.txt` file.
    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}