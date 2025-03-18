import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Process process = 
	new ProcessBuilder("git", "branch", "--show-current").start()

BufferedReader brStderr = 
	new BufferedReader(new InputStreamReader(process.getErrorStream()))
List<String> errLines = brStderr.readLines()
errLines.forEach { println it }

//The Process InputStream (our point of view) is the STDOUT from the process point of view
BufferedReader brStdout = 
	new BufferedReader(new InputStreamReader(process.getInputStream()))
List<String> outLines = brStdout.readLines()
outLines.forEach { println it }

println "exit: " + process.exitValue()

String branchName = outLines[0]
WebUI.comment("Current GIT branch: ${branchName}")
