# Prerequisites
1. Install GraalVM (https://github.com/graalvm/graalvm-ce-builds/releases)
2. Set GraalVM installation as JAVA_HOME
3. run `mvn clean package -Pbuild`
4. With any Unix shell, run `./wzrd <File Ending in .wzrd>` ex. `./wzrd samples/Addition.wzrd`


# Disclaimer
The Fork is the simplelanguage repository of GraalVM, I've used the scaffold of the simplelanguage for the language setup. All under `language` is from WZRD and from me

# Noteworthy
Similar to the features in Kotlin, `if` statements are expressions and yield their last statement as return value. The last expression in a block is it's returned value. As of right now, no `return` keyword has been implemented.
