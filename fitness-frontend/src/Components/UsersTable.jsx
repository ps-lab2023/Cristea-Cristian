import React from 'react'
import ReactModal from 'react-modal'
import { DataGrid } from '@mui/x-data-grid';
import { useEffect } from 'react';
import GetLoggedInUsersApi from '../Api/GetLoggedInUsersApi';
import { useState } from 'react';
import goBack from '../Icons/goBack.svg'

const UsersTable = ({isOpen, setOpen}) => {
    const [rows, setRows] = useState([]);
    const columns = [
        { field: 'id', headerName: 'ID', width: 100 },
        { field: 'username', headerName: 'Username', width: 150 },
        { field: 'name', headerName: 'Name', width: 140 },
        { field: 'email', headerName: 'Email', width: 300},
        { field: 'role', headerName: 'Role', width: 100},
      ];
    const getRows =  async () => {
        const users = await GetLoggedInUsersApi();
        setRows(users.map(u => { return {
            id: u.id,
            username: u.username,
            name: u.name,
            email: u.email,
            role: u.role
        }}))
    }

    useEffect(() => {
        const interval = setInterval(() => {
            getRows();
          }, 3000);
          return () => clearInterval(interval);
    }, [])

    const closeModal = () => {
        console.log("DA");
        setOpen(false);
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
                        height: '420px',
                        width: '800px',
                        padding: '20px',
                        paddingTop: '20px',
                        overflow: 'hidden'
                    },
                }}
            ariaHideApp={false}
        >
            <img src={goBack} id='go-back-icon' onClick={() => closeModal()}></img>
            <div style={{ height: 400, width: 800, paddingTop: 30 }}>
            <DataGrid
                rows={rows}
                columns={columns}
                initialState={{
                pagination: {
                    paginationModel: { page: 0, pageSize: 6 },
                },
                }}
                pageSizeOptions={[6]}
            />
            </div>
        </ReactModal>
    )
}

export default UsersTable