module.exports = {
  "extends": [
    "airbnb-typescript/base"
  ],
  "parser": "@typescript-eslint/parser",
  "plugins": [
        "@typescript-eslint/tslint"
  ],
  "rules": {
    "@typescript-eslint/indent": ['error', 2],
    "max-len": ["error", { "code": 140, "ignorePattern": "import *" }], // smaller than 140?
    "lines-between-class-members":  ["error", "always", { "exceptAfterSingleLine": true }],

    // all following rules SHOULD be removed
    "class-methods-use-this": "error",
    "import/extensions": "error",
    "import/no-unresolved": "error",
    "import/prefer-default-export": "error",
    "max-classes-per-file": "error",
    "@typescript-eslint/no-unused-vars": "error",

    // all following rules MUST be removed (mostly autofix)
    "linebreak-style": ["off", "unix"], // own PR
    "no-restricted-syntax": "error",
    "@typescript-eslint/no-use-before-define": "error",
    "@typescript-eslint/camelcase": "error",
    "no-plusplus": "error",
    "no-prototype-builtins": "error",
  }
};
