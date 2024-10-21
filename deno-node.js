import { init } from './build/small.js'

init(
  Deno.args[0],
  {
    clog: (...xs) => console.log(...xs)
  }
)
