PACKAGES = card game listchooser main map player sickness exception
MAIN = Delivery2Main Delivery3Main GameMain
TO_CLEAN = classes/ jars/GameMain.jar jars/Delivery2Main.jar jars/Delivery3Main.jar docs/
CLEAN_TERMINAL = printf "\033c"


all: play

clean:
	rm -rf $(TO_CLEAN)



$(MAIN): clean
	javac -classpath jars/json-java.jar:src -d classes src/main/$@.java



	
jar_delivery2: Delivery2Main
	jar cvfm jars/$<.jar manifest2.txt -C classes .
	
jar_delivery3: Delivery3Main
	jar cvfm jars/$<.jar manifest3.txt -C classes .	

jar: GameMain
	jar cvfm jars/$<.jar manifest.txt -C classes .



	
doc: 
	javadoc -classpath jars/json-java.jar:src -d docs -subpackages $(PACKAGES)
	$(CLEAN_TERMINAL)



	

delivery2: jar_delivery2 doc
	java -cp jars/json-java.jar:classes main.Delivery2Main
	
delivery3: jar_delivery3 doc
	java -cp jars/json-java.jar:classes main.Delivery3Main
	
play: jar doc
	java -cp jars/json-java.jar:classes main.GameMain
