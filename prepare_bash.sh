clear() {
    cd $1
    rm *iml
    rm local.properties
    rm -rf .idea
    rm copy_git_bash.sh
    rm copy_osx_bash.sh
    rm createProject.sh
    rm -rf build/
    cd "$1/app"
    rm -rf build/
}

rename() {
    cd $1
    mv com $packageDomain
    cd $packageDomain
    mv worldline $packageDomainName
    if [ $2 = true ]; then
        cd $packageDomainName
        mv kotlin_multiplatform_base $packageAppSufix
    fi
}

replace_in_files() {
    for entry in "$1/"*; do
        if [[ -d $entry ]]; then
            #echo "$entry is a directory"
            replace_in_files $entry $2 $3 $4
        elif [[ -f $entry ]]; then
            #echo "$entry is a file"
            sed -i "s/com.worldline.multiplatform/$4/g" $entry
        else
            echo "$entry is not valid"
        fi
    done

}

if [ $# -ne 2 ]; then
    echo "Please, enter the project and package name as parameter: copy.sh PROJECT_NAME PACKAGE_DOMAIN.PACKAGE_NAME.PACKAGE_SUFIX"
else
    name=$1
    packageName=$2
    packageDomain="$(cut -d'.' -f1 <<<"$2")"
    packageDomainName="$(cut -d'.' -f2 <<<"$2")"
    packageAppSufix="$(cut -d'.' -f3 <<<"$2")"

    echo "Name: $name"
    echo "Package: $packageName"

    mkdir ../$name
    cp -r * ../$name
    cp .gitignore ../$name
    cd ../$name

    path=$(pwd)

    clear $path

    # Presentation layer

    rename "$path/app/src/androidTest/java/" true
    rename "$path/app/src/main/java/" true
    rename "$path/app/src/test/java/" true

    replace_in_files "$path/app/src/androidTest/java/" $packageDomain $packageDomainName $packageName
    replace_in_files "$path/app/src/main/java/" $packageDomain $packageDomainName $packageName
    replace_in_files "$path/app/src/test/java/" $packageDomain $packageDomainName $packageName

    sed -i "s/com.worldline.multiplatform/$packageName/g" "$path/app/build.gradle"
    sed -i "s/com.worldline.multiplatform/$packageName/g" "$path/app/src/main/AndroidManifest.xml"
    sed -i "s/kotlin_multiplatform_base/$name/g" "$path/app/src/main/res/values/strings.xml"
fi
