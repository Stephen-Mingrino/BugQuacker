### Gerating token
Settings -> Developer Settings -> Personal Access Tokens -> Tokens (classic) -> Generate Classic Token
Save the token somewhere

### Adding Collaborators
Settings -> Collaborators -> Add People

### Branch Protection Rule
Settings -> Branches -> Add branch protection rule
* Type 'main' for 'Branch name pattern*'
* Check box 'Require a pull request before merging'
* Click create

### Clone repo for first time cloning from Github
        git clone <http address>

### Access Key
        cgit remote set-url origin https://<token>@github.com/<username of repo owner>/<reponame>.git

### Pulling from Github
* Before you code, every time, pull changes

        git pull origin main

### Creating Branch
        git checkout -b "<yourname>"

* Switch branch

        git checkout <branchname>

* Check which branch you're in (optional)

        git branch


### Pushing to Github
* Make sure you're in your named branch

        git add .
        git commit -m "message"
        git push -u origin <yourbranch>

* Now go to Github and create a pull request.

### Creating Pull Request
* Github -> repo -> Compare & pull request

OR

* Github -> Repo -> You Branch -> 1 commit ahead -> Create pull request

* Now someon must approve your pull request.

### Approving a Pull Request
Github -> Pull requests 
* Click on the request 
* click 'Add your review' 
* Now review the changes and resolve any merg conflicts
* If approved, Github will check for merging conflicts. If no conflicts:
* Click Merge pull request

### Reset Commit
        git push -f origin <branch>

### DONT MAKE THIS MISTAKE
* Don't pull in the middle of coding.
* Two people making changes to the same file (merge conflict) can easily be sorted out. 
* Forgetting to pull from the repo before you code can also be easily sorted out. DO NOT do a pull in the middle of coding.
Just finish your code and then push it to your branch. Then you can do a pull AFTER your push has been reviewed and uploaded.
* However lets say you forget to pull from the repo before you start coding OR someone commits while your're coding AND you accidently created a merg conflict.. then thats not good. Don't do this. Now we have two options: A) make a copy of your code, delete your project, re-clone the github, re-insert your new code into the new clone, then deal with the merg conflic. B) spend 3 hours on youtube learning what a git stash is.
* Do not code in the same file as someone else until they have pushed their code AND you have pulled that code.