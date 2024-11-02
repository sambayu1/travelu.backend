<?php

use Illuminate\Support\Facades\Route;

Route::get('/', function () {
    Route::get('/', [DashboardController::class, 'index'])->name('admin.dashboard');

    Route::get('admin', [AdminController::class, 'index'])->name('admin.admin.index');
    Route::get('admin/create', [AdminController::class, 'create'])->name('admin.admin.create');
    Route::get('admin/delete', [AdminController::class, 'delete'])->name('admin.admin.delete');
    Route::get('admin/read', [AdminController::class, 'read'])->name('admin.admin.read');

    Route::get('pelanggan', [PelangganController::class, 'index'])->name('admin.pelanggan.index');
    Route::get('pelanggan/create', [PelangganController::class, 'create'])->name('admin.pelanggan.create');
    Route::get('pelanggan/delete', [PelangganController::class, 'delete'])->name('admin.pelanggan.delete');
    Route::get('pelanggan/read', [PelangganController::class, 'read'])->name('admin.pelanggan.read');
    
    Route::get('order', [OrderController::class, 'index'])->name('admin.order.index');
    Route::get('order/create', [OrderController::class, 'create'])->name('admin.order.create');
    Route::get('order/delete', [OrderController::class, 'delete'])->name('admin.order.delete');
    Route::get('order/read', [OrderController::class, 'read'])->name('admin.order.read');
    
    Route::get('bannerhome', [BannerHomePageController::class, 'index'])->name('admin.bannerhome.index');
    Route::get('bannerhome/create', [BannerHomePageController::class, 'create'])->name('admin.bannerhome.create');
    Route::get('bannerhome/delete', [BannerHomePageController::class, 'delete'])->name('admin.bannerhome.delete');
    Route::get('bannerhome/read', [BannerHomePageController::class, 'read'])->name('admin.bannerhome.read');

    Route::get('pemesanan', [BannerHomePageController::class, 'index'])->name('admin.pemesanan.index');
    Route::get('pemesanan/create', [BannerHomePageController::class, 'create'])->name('admin.pemesanan.create');
    Route::get('pemesanan/delete', [BannerHomePageController::class, 'delete'])->name('admin.pemesanan.delete');
    Route::get('pemesanan/read', [BannerHomePageController::class, 'read'])->name('admin.pemesanan.read');

    Route::get('jadwal', [BannerHomePageController::class, 'index'])->name('admin.jadwal.index');
    Route::get('jadwal/create', [BannerHomePageController::class, 'create'])->name('admin.jadwal.create');
    Route::get('jadwal/delete', [BannerHomePageController::class, 'delete'])->name('admin.jadwal.delete');
    Route::get('jadwal/read', [BannerHomePageController::class, 'read'])->name('admin.jadwal.read');

    Route::get('csticket', [BannerHomePageController::class, 'index'])->name('admin.csticket.index');
    Route::get('csticket/create', [BannerHomePageController::class, 'create'])->name('admin.csticket.create');
    Route::get('csticket/delete', [BannerHomePageController::class, 'delete'])->name('admin.csticket.delete');
    Route::get('csticket/read', [BannerHomePageController::class, 'read'])->name('admin.csticket.read');

    Route::get('armada', [BannerHomePageController::class, 'index'])->name('admin.armada.index');
    Route::get('armada/create', [BannerHomePageController::class, 'create'])->name('admin.armada.create');
    Route::get('armada/delete', [BannerHomePageController::class, 'delete'])->name('admin.armada.delete');
    Route::get('armada/read', [BannerHomePageController::class, 'read'])->name('admin.armada.read');

    Route::get('diskon', [BannerHomePageController::class, 'index'])->name('admin.diskon.index');
    Route::get('diskon/create', [BannerHomePageController::class, 'create'])->name('admin.diskon.create');
    Route::get('diskon/delete', [BannerHomePageController::class, 'delete'])->name('admin.diskon.delete');
    Route::get('diskon/read', [BannerHomePageController::class, 'read'])->name('admin.diskon.read');
});
