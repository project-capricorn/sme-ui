npm run build
cp public/css/style.css       ../project-capricorn.github.io/css/
cp public/js/main.js ../project-capricorn.github.io/js/
cp public/index.html ../project-capricorn.github.io/
cp public/compass.jpg ../project-capricorn.github.io/
cd ../project-capricorn.github.io
git add .
git commit -m "Automated deployment commit"
git push