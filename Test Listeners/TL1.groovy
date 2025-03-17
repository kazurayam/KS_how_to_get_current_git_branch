import com.kazurayam.subprocessj.Subprocess
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.context.TestCaseContext

import internal.GlobalVariable

class TL1 {

	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		GlobalVariable.GIT_BRANCH = getCurrentGitBranch()
	}

	private String getCurrentGitBranch() {
		Subprocess.CompletedProcess cp =
			new Subprocess().run(Arrays.asList("git", "branch", "--show-current"))
		assert 0 == cp.returncode()
		List<String> stdout = cp.stdout()
		return stdout.get(0);    // will return "master" for example
	}
}
