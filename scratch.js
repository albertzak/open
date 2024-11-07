import mq from 'mqtt'

let c = mq.connect('wss://q.fxp.at:443')

c.on('error', (e) => console.error(e))
c.on('message', (m) => console.log('msg', m))

c.subscribe('t', (...e) => {
  console.log('sub', e)
  c.publish('t', 'hi')
})



