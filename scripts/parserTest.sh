cd ..
export CLASSPATH=""
for file in `ls lib`; do export CLASSPATH=$CLASSPATH:lib/$file; done
export CLASSPATH=$CLASSPATH:build/classes/main

cd test/resource/parser/
I=$(ls | wc -l)

cd ../../..

for var in $(seq $I)
do
	echo "TEST $var"
	java -cp $CLASSPATH Main test/resource/parser/file$var.ctds
	echo
done