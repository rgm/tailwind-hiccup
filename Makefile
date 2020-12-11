test: node_modules
	clojure -A:test -M -m kaocha.runner
.PHONY: test

node_modules: package.json
	npm install
	@touch node_modules

clean:
	rm -rf out/

distclean: clean
	rm -rf node_modules

