export CLASSPATH=""
for file in `ls lib`; do export CLASSPATH=$CLASSPATH:lib/$file; done
export CLASSPATH=$CLASSPATH:target/classes

cd src/test/cup/
I=$(ls | wc -l)

cd ../../..
for var in $(seq $I)
do
	echo "TEST $var"
	java -cp $CLASSPATH Main src/test/cup/file$var.ctds
	echo
done