function exportData() {
  const originalData = {
    alien: [
      {
        name: 'Marsianin',
        age: 500,
        checked: true
      },
      {
        name: 'Lunityanin',
        age: 12,
        checked: false
      },
      {
        name: 'Uranez',
        age: 0,
        checked: true
      },
      {
        name: 'Uzbekistanen',
        age: 5000,
        checked: false
      }
    ]
  }

  const a = document.createElement('a')
  a.href = URL.createObjectURL(new Blob([JSON.stringify(originalData, null, 2)],
  {
    type: 'application/json'
  }))
  a.setAttribute('download', 'alien.json')
  document.body.appendChild(a)
  a.click()
  document.body.removeChild(a)
}