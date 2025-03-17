import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Process process = 
	new ProcessBuilder("git", "branch", "--show-current").start()

BufferedReader br = 
	new BufferedReader(new InputStreamReader(process.getInputStream()))

List<String> lines = br.readLines()

String branchName = lines[0]

WebUI.comment("Current GIT branch: ${branchName}")
