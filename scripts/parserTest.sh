cd ..
export CLASSPATH=""
for file in `ls lib`; do export CLASSPATH=$CLASSPATH:lib/$file; done
export CLASSPATH=$CLASSPATH:build/classes

cd test/resource/parser/testCorrectos/
I=$(ls | wc -l)
cd ../../../..

cd test/resource/parser/testIncorrectos/
J=$(ls | wc -l)
cd ../../../..

echo "#### Test correctos ####"
for var in $(seq $I)
do
	echo "TEST $var"
	java -cp $CLASSPATH main.MainForParserTest test/resource/parser/testCorrectos/*.ctds
	echo
done

echo " "

echo "#### Test incorrectos ####"
for var in $(seq $J)
do
	echo "TEST $var"
	java -cp $CLASSPATH main.MainForParserTest test/resource/parser/testIncorrectos/*.ctds
	echo
done