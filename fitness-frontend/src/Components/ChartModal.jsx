import React from "react";
import ReactModal from "react-modal";
import ReactFC from "react-fusioncharts";
import FusionCharts from "fusioncharts";
import Column2D from "fusioncharts/fusioncharts.charts";
import FusionTheme from "fusioncharts/themes/fusioncharts.theme.fusion";
import { useState } from "react";
import GetChartDataApi from "../Api/GetChartDataApi";
import { useEffect } from "react";
import { months } from "../Utils/Constants";
import '../Styles/ChartModal.css'
import goBack from '../Icons/goBack.svg'

const ChartModal = ({isOpen, month, year, setOpen}) => {
    ReactFC.fcRoot(FusionCharts, Column2D, FusionTheme);
    const [chartData, setChartData] = useState(undefined);
    const [isSelected, setIsSelected] = useState([true, false, false])

    const getChartData = async (type) => {
        const response = await GetChartDataApi(
            JSON.parse(sessionStorage.getItem("user")).id,
            months.indexOf(month)+1,
            year,
            type
        )
        setChartData(response)
    }

    useEffect(() => {
        getChartData('DISTANCE')
    }, [])

    const closeModal = () => {
        setOpen(false);
    }

    const getTitle = () => {
        if(isSelected[0])  
            return `Distances per day in ${month} ${year}`
        if(isSelected[1])
            return `Burned calories per day in ${month} ${year}`
        if(isSelected[2])
            return `Time spent working out per day in ${month} ${year}`
    }

    const getyAxisName = () => {
        if(isSelected[0])  
            return `Distance`
        if(isSelected[1])
            return `Calories`
        if(isSelected[2])
            return `Duration`
    }

    const getNumberSuffix = () => {
        if(isSelected[0])  
            return ` km`
        if(isSelected[1])
            return ` kcal`
        if(isSelected[2])
            return ` min`
    }

    const chartConfigs = {
        type: "column2D",
        width: "700", 
        height: "400", 
        dataFormat: "json", 
        dataSource: {
          chart: {
            labelDisplay: "Auto",
            caption: getTitle(),    
            subCaption: "",           
            xAxisName: "Day",          
            yAxisName: getyAxisName(),  
            numberSuffix: getNumberSuffix(),
            palettecolors: "99C2A2",
            theme: "fusion"                
          },
          data: chartData
        }
      };
    
    const getColor = (isSelected) => {
        return {
            backgroundColor: isSelected ? "#edede9" : "transparent"
        }
    }

    const changeReport = (type) => {
        switch(type){
            case 0: getChartData('DISTANCE');
                    setIsSelected([true, false, false]);
                    break;
            case 1: getChartData('CALORIES');
                    setIsSelected([false, true, false]);
                    break;
            case 2: getChartData('DURATION');
                    setIsSelected([false, false, true]);
        }
    }

    return (
        <ReactModal
            isOpen={isOpen}
            style=
            {{
                overlay: { backgroundColor: '#00000078' },
                content: {
                    borderRadius: '20px',
                    margin: 'auto',
                    height: '403px',
                    width: '750px',
                    padding: '20px',
                    paddingTop: '20px',
                    overflow: 'hidden'
                },
            }}
            ariaHideApp={false}
        >
        <img src={goBack} id='go-back-icon' onClick={() => closeModal()}></img>
        <nav id='raport-navbar'>
            <ul>
                <li><button style={getColor(isSelected[0])} onClick={() => changeReport(0)}>Distance</button></li>
                <li><button style={getColor(isSelected[1])} onClick={() => changeReport(1)}>Calories</button></li>
                <li><button style={getColor(isSelected[2])} onClick={() => changeReport(2)}>Duration</button></li>
            </ul>
        </nav>
        <ReactFC {...chartConfigs} />
        </ReactModal>
    )
}

export default ChartModal;
