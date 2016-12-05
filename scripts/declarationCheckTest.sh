echo " "
echo "#### DeclarationCheck tests ####"

cd ..
export CLASSPATH=""
for file in `ls lib`; do export CLASSPATH=$CLASSPATH:lib/$file; done
export CLASSPATH=$CLASSPATH:build/classes

cd test/resource/visitor/declarationCheck/
I=$(ls | wc -l)
cd ../../../..

for var in $(seq $I)
do
	echo "TEST $var"
	java -cp $CLASSPATH main.MainForDeclarationTest test/resource/visitor/declarationCheck/*.ctds
	echo
done
