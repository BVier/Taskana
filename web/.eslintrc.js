module.exports = {
  "extends": [
    "airbnb-typescript/base"
  ],
  "env": {
    "browser": true,
    "node": true
  },
  "parser": "@typescript-eslint/parser",
  "parserOptions": {
    "project": "tsconfig.json",
  },
  "plugins": [
        "@typescript-eslint",
        "@typescript-eslint/tslint"
  ],
  "rules": {
    "arrow-parens": ["error", "as-needed"],
    "@typescript-eslint/indent": ['error', 2],
    "max-len": ["error", { "code": 140, "ignorePattern": "import *" }], // smaller than 140?
    "object-curly-newline": ["error", { "ImportDeclaration": "never" }],
    "quote-props": ["error", "as-needed"],
    "lines-between-class-members":  ["error", "always", { "exceptAfterSingleLine": true }],
    "comma-dangle": ["error", "only-multiline"],
    "no-underscore-dangle": ["error", { "allow": ["_links", "__karma__"] }],
    "no-param-reassign": ["error", { "props": false }],

    // all following rules SHOULD be removed
    "class-methods-use-this": "error",
    "import/extensions": "error",
    "import/no-unresolved": "error",
    "import/prefer-default-export": "error",
    "max-classes-per-file": "error",
    "@typescript-eslint/no-unused-vars": "error",

    // all following rules MUST be removed (mostly autofix)
    "linebreak-style": ["error", "unix"], // own PR
    "no-restricted-syntax": "error",
    "@typescript-eslint/no-use-before-define": "error",
    "@typescript-eslint/camelcase": "error",
    "no-plusplus": "error",
    "no-prototype-builtins": "error",
  }
};
