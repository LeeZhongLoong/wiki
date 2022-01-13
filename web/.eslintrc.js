module.exports = {
  root: true,
  env: {
    node: true
  },
  'extends': [
    'plugin:vue/vue3-essential',
    'eslint:recommended',
    '@vue/typescript/recommended'
  ],
  parserOptions: {
    ecmaVersion: 2020
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'vue/no-unused-components' : 'off',
    '@typescript-eslint/no-explicit-any' : 'off',
    'vue/no-unused-vars' : 'off',
  //  去掉没有使用参数的校验规则
    '@typescript-eslint/no-unused-varss' : 'off'

  }
}
