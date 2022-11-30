npm run build
cp public/css/style.css       ../project-capricorn.github.io/css/
cp public/js/main.js ../project-capricorn.github.io/js/
cd ../project-capricorn.github.io
git add .
git commit -m "Automated deployment commit"
git push