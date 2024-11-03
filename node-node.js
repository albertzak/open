#!/root/.nvm/versions/node/v21.7.3/bin/node

import { WebSocket } from 'ws'

globalThis.WebSocket = WebSocket

globalThis.jsImport = async (url) => {
  const p = await import(url)
  console.log('jsImport', p)
  return p
}

async function main() {
  const { init } = await import('./build/small.js')
  init(process.argv.slice(2))
}

main()
