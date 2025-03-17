# How to get the current GIT branch name in Katalon Studio

This is a small [Katalon Studio](https://katalon.com/katalon-studio) project for demonstration purpose.
You can download the zip of this project from the [Releases]() page, unzip it, open it using your Katalon Studio GUI
on your local machine, and run the demo script.

## Problem to solve

One day in the Katalon User Forum, there was a topic

- ["Is it possible to add in html or pdf reports, the git current branch?"](https://forum.katalon.com/t/is-possible-to-add-in-html-or-pdf-reports-the-git-current-branch/163746)

This topic involved several technical issues. The first obvious problem was:

*How can I get the current GIT branch name observable for a Test Case scripts in Katalon Studio?*

If you have the `git` command installed on your machine, then in the command line you can easily do this operation:

```
$ cd <projectdir>
$ git branch --show-current
master
```

The string `"master"` here is the current GIT brach name. So, I would require executing the `"git branch --show-current"` command from a Katalon test script, capture the output string from the `git` command, save the returned value into a Groovy variable, and reuse the value somehow (e.g, transfer it into a test report).

I found a Katalon documentation titled ["Execute Windows Commands in Katalon Studio](https://docs.katalon.com/katalon-studio/keywords/using-keywords-in-katalon-studio/windows-testing/execute-windows-commands-in-katalon-studio) that describes how to use `java.lang.Runtime` class in a Test Case script. I tried the way that this document describes. I made a `Test Case/TC0`, which looks:

```
// Test Cases/TC0
Runtime.getRuntime().exec("git branch --show-current")
```

I ran the `TC0` using Katalon Studio v10.1.0 on Mac. It silently PASSED. It showed the following message in the Console.

```
3月 17, 2025 12:13:18 午後 com.kms.katalon.core.logging.KeywordLogger startTest
情報: --------------------
3月 17, 2025 12:13:18 午後 com.kms.katalon.core.logging.KeywordLogger startTest
情報: START Test Cases/TC0
3月 17, 2025 12:13:19 午後 com.kms.katalon.core.logging.KeywordLogger endTest
情報: END Test Cases/TC0
```

The document didn't tell me how to read the output from the executed command. The doc is just useless.

I searched the Internet and found the following technical article, and read it.

- [How to Run a Shell Command in Java, Baeldung](https://www.baeldung.com/run-shell-command-in-java)

The Baeldung resources are good. I read the artticles and tried the sample codes. Then, I found the `java.lang.Process` and `java.lang.ProcessBuilder` are designed very versatile and are difficult to understand all about it. I realised that I need to develop a small Java library that wraps the versatile Java classes, provides a simpler API for day-to-day use.


## Solution

I read the following article:

- [Guide to java.lang.ProcessBuilder](https://www.baeldung.com/java-lang-processbuilder-api)

I made a `Test Cases/TC1`:

```
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

Process process =
	new ProcessBuilder("git", "branch", "--show-current").start()

BufferedReader br =
	new BufferedReader(new InputStreamReader(process.getInputStream()))

List<String> lines = br.readLines()

String branchName = lines[0]  // "master"

WebUI.comment("Current GIT branch: ${branchName}")
```

When I ran this, I got the following output in the console:

```
3月 17, 2025 6:07:48 午後 com.kms.katalon.core.logging.KeywordLogger startTest
情報: START Test Cases/TC1
3月 17, 2025 6:07:49 午後 com.kms.katalon.core.logging.KeywordLogger logInfo
情報: Current GIT branch: master
3月 17, 2025 6:07:49 午後 com.kms.katalon.core.logging.KeywordLogger endTest
情報: END Test Cases/TC1
```

The `TC1` could get the current GIT branch name "master". I think this is good enough.
