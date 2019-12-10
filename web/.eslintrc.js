module.exports = {
  "extends": [
    "airbnb-typescript/base"
  ],
  "env": {
    "browser": true,
    "node": true,
    "mocha": true
  },
  "parser": "@typescript-eslint/parser",
  "parserOptions": {
    "project": "tsconfig.json",
    "ecmaVersion": 5,
    "sourceType": "module"
  },
  "plugins": [
        "@typescript-eslint",
        "@typescript-eslint/tslint"
  ],
  "rules": {
    "linebreak-style": [2, "windows"],
    "import/no-extraneous-dependencies": [
      "warn",
      {
        "devDependencies": [
          "**/*test.js",
          "test/**/*.*",
          "test-util/**/*.*",
          "rollup.config.js",
          "@angular/core"
        ],
      }
    ],
    "import/no-unresolved": 0,
    "spaced-comment": 0,
    "import/extensions": 0,
    "import/prefer-default-export": 0,
    "lines-between-class-members": 0,
    "@typescript-eslint/indent": ['error', 2],
    "comma-dangle": 0,
    "import/order": 0,
    "object-curly-spacing": 0,
    "function-paren-newline": ["off", "consistent"], //TODO: consistent or other?
    "@typescript-eslint/semi": "off", //TODO
    "arrow-parens": ["error", "as-needed"],
    "max-classes-per-file": "warn",
    "no-underscore-dangle": ["warn", { "allow": ["_links", "__karma__"] }],
    "max-len": ["error", { "code": 140, "ignorePattern": "import *" }],
    "class-methods-use-this": "off", //TODO:  191 - mostly change to 'static'
    "padded-blocks": ["off", { "blocks": "never", "classes": "always" }], //TODO: no-change:167 this-one:269 - find good rules
    "@typescript-eslint/no-unused-vars": "off", //TODO: 84 - this should be error
    "@typescript-eslint/no-unused-expressions": "off", // 16, the same
    "object-curly-newline": ["error", { "ImportDeclaration": "never" }],
    "quote-props": ["warn", "as-needed"],
    "no-plusplus": "off",
    "no-useless-escape": "warn",
    "no-param-reassign": ["error", {"props": false}],
  }
};
