const canvas = document.getElementById('canvas').getContext('2d');
let chart = new Chart(canvas, {
    type: 'bar', 
    data: {
        labels: ['Cap', 'IMIW', 'BW', 'Thor'],
        datasets: [{
            label:"Favourite character",
            data: [1.84,1.94,3.21,1.25],
        backgroundColor:[
            'blue', 'red', 'black', 'yellow'
        ]
        }]
    }
})