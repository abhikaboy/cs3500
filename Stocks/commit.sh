git add .
# prompt for commit message
echo "Enter commit message: "
read commitMessage
git commit -m "$commitMessage"
git push 